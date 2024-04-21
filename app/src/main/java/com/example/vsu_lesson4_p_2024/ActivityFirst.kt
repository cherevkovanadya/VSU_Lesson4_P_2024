package com.example.vsu_lesson4_p_2024

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import java.io.Serializable

private const val EXTRA_FIRST = "EXTRA_FIRST"
private const val EXTRA_FIRST_RESULT = "EXTRA_FIRST_RESULT"
private const val EXTRA_BUNDLE_FIRST = "EXTRA_BUNDLE_FIRST"
private const val EXTRA_BUNDLE_SECOND = "EXTRA_BUNDLE_SECOND"

class ActivityFirst : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val resultListenerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val receivedData = result.data?.extras?.getBundle(EXTRA_FIRST_RESULT)
                val user = receivedData?.getSerializable(EXTRA_BUNDLE_SECOND) as? User
                Toast.makeText(this, user?.name, Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.go_btn).setOnClickListener {
            //Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ActivitySecond::class.java)
            intent.putExtra(EXTRA_FIRST, "Hello World!")
//            bundleOf(
//                EXTRA_FIRST to "Hello World!"
//            )
            resultListenerLauncher.launch(intent)
        }

    }
}

data class User(
    val name: String,
    val address: Address,
) : Serializable

data class Address(
    val path: String
) : Serializable

class ActivitySecond : AppCompatActivity(R.layout.activity_second) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let { screenData ->
            val data = screenData.getString(EXTRA_FIRST)
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.go_back_btn).setOnClickListener {
            val resultIntent = Intent().apply {
                val user = User("Shlepa", Address("123"))
                val bundle = bundleOf(
                    EXTRA_BUNDLE_FIRST to "John",
                    EXTRA_BUNDLE_SECOND to user
                )
                putExtra(EXTRA_FIRST_RESULT, bundle)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
