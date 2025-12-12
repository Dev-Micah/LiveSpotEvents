package com.micahnyabuto.livespotevents.ui.auth

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.micahnyabuto.livespotevents.ui.auth.register.AuthTextField

// Other imports...
import androidx.lifecycle.viewmodel.compose.viewModel
import com.micahnyabuto.livespotevents.ui.auth.login.LoginViewModel
import androidx.compose.runtime.LaunchedEffect


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    // Observe navigation events from the ViewModel
    LaunchedEffect(key1 = Unit) {
        loginViewModel.navigationEvent.collect { route ->
            navController.navigate(route)
        }
    }

    // Get state from the ViewModel
    val email = loginViewModel.email
    val password = loginViewModel.password

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            modifier = Modifier
                .padding(top = 130.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        AuthTextField(
            value = email,
            // Call ViewModel methods on value change
            onValueChange = { loginViewModel.onEmailChange(it) },
            label = "Enter your email",
            placeholder = "name@example.com"
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            // Call ViewModel methods on value change
            onValueChange = { loginViewModel.onPasswordChange(it) },
            label = "Enter your password",
            placeholder = "password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Delegate the click event to the ViewModel
                loginViewModel.onSignInClicked()
            },
            modifier = Modifier
                .clip(RoundedCornerShape(9.dp))
                .fillMaxWidth(),
        ) {
            Text(text = "Sign In")
        }

        // ... (rest of your ClickableText code remains the same)
        val annotatedString = buildAnnotatedString {
            append("Don't have an account? ")
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(tag = "SIGNUP", annotation = "signup")
                append("Sign up")
                pop()
            }
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "SIGNUP", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navController.navigate("register")
                    }
            },
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
