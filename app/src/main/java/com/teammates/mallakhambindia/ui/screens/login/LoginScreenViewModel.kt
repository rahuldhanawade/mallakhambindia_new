package com.teammates.mallakhambindia.ui.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.teammates.mallakhambindia.data.RequestModel.LoginRequestModel
import com.teammates.mallakhambindia.data.Resource
import com.teammates.mallakhambindia.data.ResponseModel.UserDetailsResponse
import com.teammates.mallakhambindia.repository.MainRepository
import com.teammates.mallakhambindia.utils.MyValidator
import com.teammates.mallakhambindia.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val mainRepository: MainRepository
) : ViewModel() {

    val TAG = "LoginScreenViewModel"

    val myValidator = MyValidator

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

    private val _loginData = MutableStateFlow("Select Location")
    val LoginData: StateFlow<String> = _loginData.asStateFlow()

    private val _userDetails = MutableStateFlow<Resource<UserDetailsResponse>>(Resource.Loading)
    val userDetails: StateFlow<Resource<UserDetailsResponse>> = _userDetails.asStateFlow()

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome

    init {
        fetchLocations()
    }


    fun resetNavigation() {
        _navigateToHome.value = false
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _isEmailValid.value = myValidator.isValidField(newEmail)
        validateForm()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _isPasswordValid.value = myValidator.isValidPassword(newPassword)
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

    private fun fetchLocations() {
        viewModelScope.launch {
            mainRepository.getLocations()
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
//        Log.d(TAG, "onLogin: "+loginRequestModel)

        viewModelScope.launch {
            mainRepository.getUserLogin(loginRequestModel)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _loginData.value = Resource.Error("Error during login: ${e.message}").toString()
                }
                .collect { response ->
                    if (response.success == true) {
                        _loginData.value = Resource.Success(response.token).toString()
                        getUserDetails(response.token.toString())
                    } else {
                        _loginData.value = Resource.Error("Login failed").toString()
                    }
                }
        }
    }

    fun getUserDetails(token: String) {
        viewModelScope.launch {
            mainRepository.getUserDetails(token)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _userDetails.value = Resource.Error("Error: ${e.message}")
                }
                .collect { response ->
                    val jsonResponse = Gson().toJson(response)
                    sharedPreferencesHelper.saveBoolean("is_logged_in", true)
                    sharedPreferencesHelper.saveString("token", token)
                    sharedPreferencesHelper.saveString("userDetails", jsonResponse)
                    _navigateToHome.value = true
//                    _userDetails.value = Resource.Success(response)
                }
        }
    }


    fun onLogin() {
        if (isValidate()) {
//            Log.d(TAG, "onLogin: "+email.value+password.value+selectedLocation.value)
            login(email.value, password.value,selectedLocation.value)
        }
    }

    private fun isValidate(): Boolean {
        return _isEmailValid.value &&
                _isPasswordValid.value &&
                _selectedLocation.value != "Select Location"
    }
}
