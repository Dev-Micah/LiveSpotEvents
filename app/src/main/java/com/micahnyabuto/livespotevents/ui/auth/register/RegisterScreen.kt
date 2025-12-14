package com.micahnyabuto.livespotevents.ui.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    // Observe navigation events from the ViewModel
    LaunchedEffect(key1 = Unit) {
        registerViewModel.navigationEvent.collect { route ->
            navController.navigate(route)
        }
    }

    // Get state from the ViewModel
    val email = registerViewModel.email
    val password = registerViewModel.password
    val confirmPassword = registerViewModel.confirmPassword

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            modifier = Modifier
                .padding(top = 130.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        AuthTextField(
            value = email,
            onValueChange = { registerViewModel.onEmailChange(it) },
            label = "Enter your email",
            placeholder = "name@example.com"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = { registerViewModel.onPasswordChange(it) },
            label = "Enter your password",
            placeholder = "password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = confirmPassword,
            onValueChange = { registerViewModel.onConfirmPasswordChange(it) },
            label = "Confirm your password",
            placeholder = "password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Delegate the click event to the ViewModel
                registerViewModel.onSignUpClicked()
            },
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .fillMaxWidth(),
        ) {
            Text(text = "Sign Up")
        }

        // --- Rest of your ClickableText code remains the same ---
        val annotatedString = buildAnnotatedString {
            append("Already have an account? ")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(tag = "SIGNIN", annotation = "signin")
                append("Sign in")
                pop()
            }
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "SIGNIN", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate("login")
                    }
            },
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// --- Your AuthTextField composable remains the same ---
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        isError = isError,
        supportingText = {
            if (supportingText != null) {
                Text(text = supportingText)
            }
        }
    )
}
