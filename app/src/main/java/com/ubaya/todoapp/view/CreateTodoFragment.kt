package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.todoapp.R
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel:DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        btnCreateTodo.setOnClickListener {
            var todo = Todo(txtTitle.text.toString(),txtNotes.text.toString())
            viewModel.addTodo(todo)
            Toast.makeText(it.context,"Todo Created",Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }
}