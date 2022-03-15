package com.careindia.lifeskills.views.loginscreen

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.careindia.lifeskills.R
import com.careindia.lifeskills.utils.Progress
import com.careindia.lifeskills.views.collectivemeeting.CollectiveMeetingListActivity
import com.careindia.lifeskills.views.homescreen.HomeDashboardActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_webview_login.*

class WebviewLoginActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var progress: Progress? = null
    private var isLoaded: Boolean = false
    private var doubleBackToExitPressedOnce = false
    private var webURL = "http://ewp.careindia.org/" // Change it with your URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_login)
        webView = findViewById(R.id.webView)
//        webView.settings.setJavaScriptEnabled(true)

//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                view?.loadUrl(url!!)
//                return true
//            }
//        }
//        webView.loadUrl("http://ewp.careindia.org/")

        webView.settings.javaScriptEnabled = true
        if (!isOnline()) {
            showToast(getString(R.string.no_internet))
            infoTV.text = getString(R.string.no_internet)
            showNoNetSnackBar()
            return
        }

    }

    override fun onResume() {
        if (isOnline() && !isLoaded) loadWebView()
        super.onResume()
    }

    private fun loadWebView() {
        infoTV.text = ""
        webView.loadUrl(webURL)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
                Log.i("care india web", "shouldOverrideUrlLoading")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                setProgressDialogVisibility(true)
                super.onPageStarted(view, url, favicon)

                Log.i("careindiawebTest", "onPageStarted " + "$url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                isLoaded = true
                setProgressDialogVisibility(false)
                super.onPageFinished(view, url)

                Log.i("careindiawebTest", "onPageFinished " + "$url")

                var list = url?.split("&")
                var trainerName = list.toString().replace("[", "").replace("]", "")
                val listsec = trainerName.split("username=")
                var trainerName2 = listsec.toString().replace("[", "").replace("]", "")
                val listthird = trainerName2.split(",")
                if (listthird != null && listthird.size > 10) {
                    var aa = listthird.get(10).toString()
                    println(aa)

                    nextActivity(1)
                }

            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                isLoaded = false
                val errorMessage = "Got Error! $error"
                showToast(errorMessage)
                infoTV.text = errorMessage
                setProgressDialogVisibility(false)
                super.onReceivedError(view, request, error)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    showToastToExit()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showToastToExit() {
        when {
            doubleBackToExitPressedOnce -> {
                onBackPressed()
            }
            else -> {
                doubleBackToExitPressedOnce = true
                showToast(getString(R.string.back_again_to_exit))
                Handler(Looper.myLooper()!!).postDelayed(
                    { doubleBackToExitPressedOnce = false },
                    2000
                )
            }
        }
    }

    private fun setProgressDialogVisibility(visible: Boolean) {
        if (visible) progress = Progress(this, R.string.please_wait, cancelable = true)
        progress?.apply { if (visible) show() else dismiss() }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNoNetSnackBar() {
        val snack =
            Snackbar.make(rootView, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
        snack.setAction(getString(R.string.settings)) {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        snack.show()
    }

    private fun nextActivity(flag: Int) {
        if (flag == 1) {
            val i = Intent(this, HomeDashboardActivity::class.java)
            startActivity(i)
            finish()
        }
    }


//    fun ActionResultHome()
//    {
//        DataTable dt = new DataTable();
//        var samlEndpoint = ConfigurationManager.AppSettings["samlEndpoint"];
//
//        dt.Columns.AddRange(new DataColumn[2] { new DataColumn("SAML Attribute"), new DataColumn("SAML Attribute Value") });
//        var request = new AuthRequest(
//                ConfigurationManager.AppSettings["AppUniqueID"], //put your app's "unique ID" here
//        ConfigurationManager.AppSettings["AppURL"] //assertion Consumer Url - the redirect URL where the provider will send authenticated users
//        );
//        if (DownloadManager.Request.Form["SAMLResponse"] != null)
//        {
//            Saml.Response samlResponse = new Response(ConfigurationManager.AppSettings["samlCertificate"]);
//            samlResponse.LoadXmlFromBase64(Request.Form["SAMLResponse"]); //SAML providers usually POST the data into this var
//
//            if (samlResponse.IsValid())
//            {
//                //WOOHOO!!! user is logged in
//                //YAY!
//
//                //Some more optional stuff for you
//                //lets extract username/firstname etc
//                string username;
//                try
//                {
//                    username = samlResponse.GetNameID();
//                    //username = "FC";
//                    Session["userName"] = username;
//
//                    SqlParameter[] p = new SqlParameter[] {
//                        new SqlParameter("UserID",username)
//                    };
//                    dt = SqlHelper.GetDataTable(SqlHelper.mainConnectionString, CommandType.StoredProcedure, "User_Registration", p);
//                    if (dt.Rows.Count > 0)
//                    {
//                        Session["RoleID"] = Convert.ToString(dt.Rows[0]["Role"]);
//                        Session["UserID"] = Convert.ToString(dt.Rows[0]["RID"]);
//                        Session["User"] = Convert.ToString(dt.Rows[0]["UserName"]);
//                        //string url = ConfigurationManager.AppSettings["ReplyURL"];
//                        //Response.Redirect(url);
//                    }
//                    else
//                    {
//
//                    }
//
//                }
//                catch (Exception ex)
//                {
//                }
//            }
//        }
//        else
//        {
//            string url = request.GetRedirectUrl(samlEndpoint);
//            Response.Redirect(url);
//        }
//
//        return View();
//    }
}