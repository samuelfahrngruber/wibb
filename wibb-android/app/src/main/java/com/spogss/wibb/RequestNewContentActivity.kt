package com.spogss.wibb

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spogss.wibb.connection.WibbConnection
import kotlinx.android.synthetic.main.activity_request_new_content.*

class RequestNewContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_new_content)
    }

    fun submitRequest(view: View) {
        if (view.id == R.id.button_submitRequest) {
            WibbConnection.addRequest(editText_requestText.text.toString()) {
                if (it) {
                    Toast.makeText(this, R.string.toast_contentRequest_success, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this, R.string.toast_contentRequest_fail, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
