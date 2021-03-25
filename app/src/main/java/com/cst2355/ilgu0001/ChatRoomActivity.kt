package com.cst2355.ilgu0001

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity


class ChatRoomActivity : AppCompatActivity() {

    private val messages: ArrayList<Messages> = ArrayList()
    private  val listAdapter = MyListAdapter()
    private lateinit var database: SQLiteDatabase

    companion object {
        val MESSAGE = "ITEM"
        val ITEM_POSITION = "POSITION"
        val ITEM_ID = "ID"
        val SEND_REC = "SEND"
    }

    var companion = Companion

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val isTablet = findViewById<View?>(R.id.fragment) != null
        loadData()

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
                database.delete(DatabaseControl.TABLE_NAME, "_id=?", arrayOf(id.toString()))

                messages.removeAt(position)
                listAdapter.notifyDataSetChanged()
            }
            alertDialogBuilder.setNegativeButton("No") { _, _ -> } //Nothing happens

            alertDialogBuilder.create().show(); true
        }

        sendButton.setOnClickListener {
            val message: String = editText.text.toString()
            val isSend = 1

            val newRowValues = ContentValues()
            newRowValues.put(DatabaseControl.COL_MESSAGE, message)
            newRowValues.put(DatabaseControl.COL_SEND, isSend)

            val newId: Long = database.insert(DatabaseControl.TABLE_NAME, null, newRowValues)

            val sendMessage = Messages(editText.text.toString(), 1, newId)
            messages.add(sendMessage)
            listAdapter.notifyDataSetChanged()
            editText.text.clear()
        }

        receiveButton.setOnClickListener {
            val message: String = editText.text.toString()
            val isSend = 0

            val newRowValues = ContentValues()
            newRowValues.put(DatabaseControl.COL_MESSAGE, message)
            newRowValues.put(DatabaseControl.COL_SEND, isSend)

            val newId: Long = database.insert(DatabaseControl.TABLE_NAME, null, newRowValues)

            val sendMessage = Messages(editText.text.toString(), 0, newId)
            messages.add(sendMessage)
            listAdapter.notifyDataSetChanged()
            editText.text.clear()
        }

        listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, id: Long ->

                val dataToPass = Bundle()
                dataToPass.putString(MESSAGE, messages[position].msg)
                dataToPass.putInt(ITEM_POSITION, position)
                dataToPass.putLong(ITEM_ID, id)
                dataToPass.putInt(SEND_REC, messages[position].isSend)
                if (isTablet) {
                    val fragment = DetailsFragment()
                    fragment.arguments = dataToPass
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment, fragment)
                        .commit()
                } else {
                    val nextActivity = Intent(this@ChatRoomActivity, EmptyActivity::class.java)
                    nextActivity.putExtras(dataToPass)
                    startActivity(nextActivity)
                }
            }
    }

    private fun loadData() {
        val dbManager = DatabaseControl(this)
        database = dbManager.writableDatabase
        val columns = arrayOf<String>(DatabaseControl.COL_ID, DatabaseControl.COL_MESSAGE, DatabaseControl.COL_SEND)

        val results: Cursor = database.query(false, dbManager.companion.TABLE_NAME, columns, null, null, null, null, null, null)
        val messageColumnIndex = results.getColumnIndex(dbManager.companion.COL_MESSAGE)
        val sendTypeColumnIndex = results.getColumnIndex(dbManager.companion.COL_SEND)
        val idColumnIndex = results.getColumnIndex(dbManager.companion.COL_ID)

        while (results.moveToNext()) {
            val sendType = results.getInt(sendTypeColumnIndex)
            val message = results.getString(messageColumnIndex)
            val id = results.getLong(idColumnIndex)
            messages.add(Messages(message, sendType, id))
        }
        this.printCursor(results, database.version)
    }

    private fun printCursor(cursor: Cursor, version: Int) {
        if (cursor.moveToFirst()) {
            val columnNames: Array<String> = cursor.columnNames

            Log.e("Database Version Number: ", version.toString())
            Log.e("Number of columns: ", cursor.columnCount.toString())
            Log.e("Column names: ", columnNames.toString())
            Log.e("Number of rows: ", cursor.count.toString())
            val msgIndex = cursor.getColumnIndex(DatabaseControl.COL_MESSAGE);
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {

                Log.e("ChatRoomActivity", "|    " + cursor.getString(msgIndex))
                cursor.moveToNext()
            }
        }
    }

    class Messages(public val msg: String, public val isSend: Int, val id: Long)

    inner class MyListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return messages.size
        }

        override fun getItem(position: Int): Messages {
            return messages[position]
        }

        override fun getView(position: Int, old: View?, parent: ViewGroup): View {

            val message: Messages = getItem(position)
            val inflater = layoutInflater
            lateinit var lastView: View

            lastView = if (message.isSend == 1) {
                inflater.inflate(R.layout.activity_chat_sent, parent, false)
            } else inflater.inflate(R.layout.activity_chat_received, parent, false)

            val textView = lastView.findViewById(R.id.textField) as TextView
            textView.text = getItem(position).msg

            return lastView
        }

        override fun getItemId(position: Int): Long {
            return getItem(position).id;
        }
    }

    class DatabaseControl(ctx: Context?) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, VERSION_NUM) {

        companion object {
            public const val DATABASE_NAME = "ChatRoom"
            public const val VERSION_NUM = 2
            public const val TABLE_NAME = "Messages"
            public const val COL_MESSAGE = "Message"
            public const val COL_SEND = "Send"
            public const val COL_ID = "_id"
        }

        var companion = Companion //To access the table name

        override fun onCreate(database: SQLiteDatabase) {

            database!!.execSQL("CREATE TABLE $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, $COL_MESSAGE  text, $COL_SEND INTEGER);")
        }

        override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            database!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(database)
        }

        override fun onDowngrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            database!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(database)
        }
    }
}

