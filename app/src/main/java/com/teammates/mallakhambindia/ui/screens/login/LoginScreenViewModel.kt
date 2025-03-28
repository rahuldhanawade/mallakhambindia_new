package com.teammates.mallakhambindia.ui.screens.login

import RetrofitBuilder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammates.mallakhambindia.data.ApiHelperImpl
import com.teammates.mallakhambindia.data.RequestModel.LoginRequestModel
import com.teammates.mallakhambindia.data.Resource
import com.teammates.mallakhambindia.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    var showDialog = true

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isEmailValid = MutableStateFlow(true)
    val isEmailValid: StateFlow<Boolean> = _isEmailValid.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(true)
    val isPasswordValid: StateFlow<Boolean> = _isPasswordValid.asStateFlow()

    private val _selectedLocation = MutableStateFlow("Select Location")
    val selectedLocation: StateFlow<String> = _selectedLocation.asStateFlow()

    private val _isLoginEnabled = MutableStateFlow(false)
    val isLoginEnabled: StateFlow<Boolean> = _isLoginEnabled.asStateFlow()

    private val _locationListData = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val locationListData: StateFlow<Resource<List<String>>> = _locationListData.asStateFlow()

    private val apiHelper = ApiHelperImpl(RetrofitBuilder.apiService)

    init {
        fetchLocations()
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _isEmailValid.value = isValidEmail(newEmail)
        validateForm()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _isPasswordValid.value = isValidPassword(newPassword)
        validateForm()
    }

    fun onLocationSelected(location: String) {
        _selectedLocation.value = location
        validateForm()
    }

    private fun validateForm() {
        _isLoginEnabled.value = _isEmailValid.value &&
                _isPasswordValid.value &&
                _selectedLocation.value != "Select Location"
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6 && password.any { it.isDigit() } && password.any { it.isLetter() }
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            apiHelper.getLocations()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _locationListData.value = Resource.Error("Error fetching data: ${e.message}")
                }
                .collect { response ->
                    val locations = mutableListOf("Select Location")
                    locations.addAll(response.data)
                    _locationListData.value = Resource.Success(locations)
                }
        }
    }

    private fun login(email: String, password: String, selectedLocation: String) {
        val loginRequestModel = LoginRequestModel(email, password, selectedLocation)

        viewModelScope.launch {
            apiHelper.getUserLogin(loginRequestModel)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _locationListData.value = Resource.Error("Error fetching data: ${e.message}")
                }
                .collect { response ->
                    if (response.success == true) {
                        _locationListData.value = Resource.Success(response.data)
                    } else {
                        _locationListData.value = Resource.Error("Login failed")
                    }
                }
        }
    }


    fun onLogin() {
        if (isValidate()) {
            login(email.toString(), password.toString(),selectedLocation.toString())
        }
    }

    private fun isValidate(): Boolean {
        return _isEmailValid.value &&
                _isPasswordValid.value &&
                _selectedLocation.value != "Select Location"
    }
}
