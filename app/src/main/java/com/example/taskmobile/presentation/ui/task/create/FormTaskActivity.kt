package com.example.taskmobile.presentation.ui.task.create

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskmobile.core.AppConstants
import com.example.taskmobile.core.UiEvent
import com.example.taskmobile.data.model.Task
import com.example.taskmobile.databinding.ActivityFormTaskBinding
import com.example.taskmobile.presentation.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FormTaskActivity : AppCompatActivity(), OnDateSetListener, OnTimeSetListener {

    private val binding by lazy { ActivityFormTaskBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<FormTaskViewModel>()
    private val calender = Calendar.getInstance()

    private var editTask: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        btnListeners()

        observerViewModel()

        loadDataFromActivity()

    }


    private fun btnListeners(){
        binding.ivBack.setOnClickListener { backToHomeActivity() }

        binding.btnCancel.setOnClickListener { backToHomeActivity() }

        binding.etDate.setOnClickListener {
            DatePickerDialog(
                this,
                this,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.etTime.setOnClickListener{
            TimePickerDialog(
                this,
                this,
                calender.get(Calendar.HOUR_OF_DAY),
                calender.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.btnSave.setOnClickListener {
            verifyInputs()
        }

    }

    private fun backToHomeActivity(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfWeek: Int) {
        calender.set(year, month, dayOfWeek)
        val currentDay = Calendar.getInstance().apply {
            get(Calendar.YEAR)
            get(Calendar.MONTH)
            get(Calendar.DAY_OF_MONTH)
        }

        if(calender.after(currentDay)){
            val date: String = SimpleDateFormat("dd/MM/yyyy").format(calender.timeInMillis)
            binding.etDate.setText(date)
            binding.tilDate.isErrorEnabled = false
        }else{
            binding.tilDate.error = "Data inválida!!"
            Toast.makeText(
                this,
                "Data inválida!!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onTimeSet(view: TimePicker?, hours: Int, minutes: Int) {
        calender.apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
        }

        val hour =  SimpleDateFormat("HH:mm").format(calender.timeInMillis)
        binding.etTime.setText(hour)
    }

    private fun verifyInputs(){
        var next = true

        if(binding.tilName.editText?.text?.isEmpty() == true){
            binding.tilName.error = "Nome não pode ser vazio!!"
            next = false
        }else{
            binding.tilName.isErrorEnabled = false
        }

        if(binding.tilDescription.editText?.text?.isEmpty() == true){
            binding.tilDescription.error = "Descrição não pode ser vazia!!"
            next = false
        }
        else{
            binding.tilDescription.isErrorEnabled = false
        }

        if(binding.tilDate.editText?.text?.isEmpty() == true){
            binding.tilDate.error = "Data não pode ser vazia!!"
            next = false
        }else{
            binding.tilDate.isErrorEnabled = false
        }

        if(binding.tilTime.editText?.text?.isEmpty() == true){
            binding.tilTime.error = "Horas não pode ser vazio"
            next = false
        }else{
            binding.tilTime.isErrorEnabled = false
        }

        if(!next){
            Toast.makeText(this, "Campos inválidos!!", Toast.LENGTH_LONG).show()
            return
        }

        sendTask()
    }

    private fun sendTask(){
        val date = binding.etDate.text.toString() + " " + binding.etTime.text.toString()

        val task = Task(
            name = binding.tilName.editText?.text.toString(),
            description = binding.tilDescription.editText?.text.toString(),
            date = date
        )

        if(!editTask.isNullOrEmpty()){
            task.id = editTask
            viewModel.updateTask(task)
        }else{
            viewModel.createTask(task)
        }
        backToHomeActivity()
    }

    private fun loadDataFromActivity(){
        val bundle = intent.extras
        if(bundle != null){
            editTask = bundle.getString(AppConstants.BUNDLE.IDTASK)!!
            viewModel.findTask(editTask!!)
        }
    }

    private fun observerViewModel(){
        viewModel.editTask.observe(this){
            if(it !== null){
                binding.tilName.editText?.setText(it.name)
                binding.tilDescription.editText?.setText(it.description)
                val infoDate = it.date.split(" ")
                binding.tilDate.editText?.setText(infoDate[0])
                binding.tilTime.editText?.setText(infoDate[1])
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collectLatest { result ->
                when(result){
                    is UiEvent.ShowToast ->
                        Toast.makeText(applicationContext, result.text, Toast.LENGTH_LONG).show()

                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(binding.root, result.text, Snackbar.LENGTH_LONG)
                        snackbar.show()

                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

}