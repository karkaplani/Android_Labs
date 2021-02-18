package com.cst2355.ilgu0001

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatRoomActivity : AppCompatActivity() {

    private val messages: ArrayList<Messages> = ArrayList()
    private  val listAdapter = MyListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val listView= findViewById<ListView>(R.id.myList)
        val editText = findViewById<EditText>(R.id.typeArea)
        val sendButton: Button = findViewById(R.id.sendButton)
        val receiveButton: Button = findViewById(R.id.receiveButton)

             listView.adapter = listAdapter
        listView.setOnItemLongClickListener { _, _, position, id ->

            val alertDialogBuilder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

            alertDialogBuilder.setTitle("Do you want to delete this?")
            alertDialogBuilder.setMessage("The selected row is: $position \n The database id is: $id")

            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                messages.removeAt(position)
                listAdapter.notifyDataSetChanged()
            }
            alertDialogBuilder.setNegativeButton("No") { _, _ -> } //Nothing happens

            alertDialogBuilder.create().show(); true
        }

        sendButton.setOnClickListener {
            messages.add(Messages(editText.text.toString(), true))
            listAdapter.notifyDataSetChanged()
            editText.setText("")
        }

        receiveButton.setOnClickListener {
            messages.add(Messages(editText.text.toString(), false))
            listAdapter.notifyDataSetChanged()
            editText.setText("")
        }
    }

    class Messages(public val msg: String, public val isSend: Boolean)

    inner class MyListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return messages.size
        }

        override fun getItem(position: Int): Messages {
            return messages[position]
        }


        override fun getView(position: Int, old: View?, parent: ViewGroup): View {

            val message: Messages = getItem(position) as Messages
            val inflater = layoutInflater
            lateinit var lastView: View

            lastView = if (message.isSend) {
                inflater.inflate(R.layout.activity_chat_sent, parent, false)
            } else inflater.inflate(R.layout.activity_chat_received, parent, false)

            val textView = lastView.findViewById(R.id.textField) as TextView
            textView.text = getItem(position).msg

            return lastView
        }



        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }
}

