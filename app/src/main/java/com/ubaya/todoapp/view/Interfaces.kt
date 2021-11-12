package com.ubaya.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.ubaya.todoapp.model.Todo

interface TodoCheckChangeListener {
    fun onTodoCheckedChange(cb:CompoundButton,isChecked:Boolean,obj: Todo)
}

interface  TodoEditClickListener {
    fun onTodoEditClick(v: View)
}

interface RadioClickListener {
    fun onRadioClick(v: View, obj: Todo)
}

interface TodoSaveChangesListener{
    fun onTodoSaveChanges(v: View,obj: Todo)
}

interface ButtonAddTodoClickListener{
    fun onButtonAddTodo(v: View)
}

interface DateClickListener {
    fun onDateClick(v: View)
}

interface TimeClickListener {
    fun onTimeClick(v: View)
}