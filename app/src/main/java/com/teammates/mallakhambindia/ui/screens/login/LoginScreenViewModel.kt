package com.teammates.mallakhambindia.ui.screens.login

import RetrofitBuilder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammates.mallakhambindia.data.ApiHelperImpl
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
        _isEmailValid.value = android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
        validateForm()
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        _isPasswordValid.value = newPassword.length >= 6
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

    fun onLogin() {
        if (isValidate()) {
            sharedPreferencesHelper.saveString("email", _email.value)
            sharedPreferencesHelper.saveString("password", _email.value)
        }
    }

    private fun isValidate(): Boolean {
        return _isEmailValid.value &&
                _isPasswordValid.value &&
                _selectedLocation.value != "Select Location"
    }
}
