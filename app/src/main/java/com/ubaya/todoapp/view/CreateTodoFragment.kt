package com.ubaya.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.ubaya.todoapp.R
import com.ubaya.todoapp.databinding.FragmentCreateTodoBinding
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.util.NotificationHelper
import com.ubaya.todoapp.util.TodoWorker
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*
import kotlinx.android.synthetic.main.fragment_create_todo.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.min

class CreateTodoFragment : Fragment(),ButtonAddTodoClickListener,RadioClickListener,
    DateClickListener,TimeClickListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    private lateinit var viewModel:DetailTodoViewModel
    private lateinit var dataBinding:FragmentCreateTodoBinding
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create_todo,container,false)
        return dataBinding.root
    //        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        dataBinding.todo = Todo("","",3,0,0)
        dataBinding.listener = this
        dataBinding.radiolistener = this
        dataBinding.listenerDate = this
        dataBinding.listenerTime = this
//        btnCreateTodo.setOnClickListener {
//            val myWorkReguest = OneTimeWorkRequestBuilder<TodoWorker>()
//                .setInitialDelay(10,TimeUnit.SECONDS)
//                .setInputData(workDataOf("TITLE" to "todo created",
//                    "MESSAGE" to "A New todo has been created! Stay Focused!"))
//                .build()
//            WorkManager.getInstance(requireContext()).enqueue(myWorkReguest)
//            val radio = view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
//            var todo = Todo(txtTitle.text.toString(),txtNotes.text.toString(),radio.tag.toString().toInt(),0)
//            viewModel.addTodo(todo)
//            Toast.makeText(it.context,"Todo Created",Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
        }

    override fun onRadioClick(v: View, obj: Todo) {
        obj.priority = v.tag.toString().toInt()
    }

    override fun onButtonAddTodo(v: View) {
        val c = Calendar.getInstance()
        c.set(year,month,day,hour,minute,0)
        val today = Calendar.getInstance()
        val diff = (c.timeInMillis/1000L) - (today.timeInMillis/1000L)
        dataBinding.todo!!.todo_date=(c.timeInMillis/1000L).toInt()
        viewModel.addTodo(dataBinding.todo!!)
        Toast.makeText(v.context,"Todo Created",Toast.LENGTH_SHORT).show()

        val myWorkReguest = OneTimeWorkRequestBuilder<TodoWorker>()
            .setInitialDelay(diff,TimeUnit.SECONDS)
            .setInputData(workDataOf("TITLE" to "todo created",
                "MESSAGE" to "A New todo has been created! Stay Focused!"))
            .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkReguest)
        Navigation.findNavController(v).popBackStack()
    }

    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        activity?.let{
            it -> DatePickerDialog(it,this,year,month,day).show()
        }
    }

    override fun onTimeClick(v: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(activity,this,hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Calendar.getInstance().let {
            it.set(year,month,dayOfMonth)
            dataBinding.root.txtDate.setText(
                dayOfMonth.toString().padStart(2,'0')+ "-"+
                        (month+1).toString().padStart(2,'0')+ "-" +
                    year.toString().padStart(2,'0'))
            this.year = year
            this.month = month
            this.day = dayOfMonth
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dataBinding.root.txtTime.setText(
            hourOfDay.toString().padStart(2,'0')+ ":"+
                    minute.toString().padStart(2,'0')
        )
        hour=hourOfDay
        this.minute = minute
    }
}