package com.example.todo_app.todo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todo_app.todo.models.TodoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoViewModel : ViewModel(){
    private var _todo = MutableStateFlow<List<TodoModel>>(emptyList())
    val todo: StateFlow<List<TodoModel>> get() = _todo
private  var id=1;

    fun addTodo(todos:String){
        var newTodo=TodoModel(id = id++,todo=todos)
        _todo.value += newTodo
    }
    fun deleteTodo(id : Int){
        _todo.value=_todo.value.filter {  it.id!=id}
    }
}