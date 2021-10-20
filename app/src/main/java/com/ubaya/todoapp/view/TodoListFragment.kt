package com.ubaya.todoapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.todoapp.R
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.viewmodel.ListTodoViewModel
import kotlinx.android.synthetic.main.fragment_todo_list.*


class TodoListFragment : Fragment() {

    private lateinit var viewModel:ListTodoViewModel
    private var todoListAdapter:TodoListAdapter = TodoListAdapter(arrayListOf(), { item -> doClick(item) })
    fun doClick(item:Int){
        viewModel.updateTodoDone(item)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()

        recTodoList.layoutManager = LinearLayoutManager(context)
        recTodoList.adapter = todoListAdapter

        fabAdd.setOnClickListener{
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            todoListAdapter.updateTodoList(it)
            with(txtEmpty){
                visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
            }
        })
    }

}