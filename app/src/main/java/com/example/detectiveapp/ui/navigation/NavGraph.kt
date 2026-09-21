package com.example.detectiveapp.ui.navigation

import com.example.detectiveapp.logic.CaseViewModel
import com.example.detectiveapp.logic.CaseViewModelFactory
import com.example.detectiveapp.ui.screens.caselist.CaseListScreen
import com.example.detectiveapp.ui.screens.caseform.CaseFormScreen
import com.example.detectiveapp.ui.screens.casedetail.CaseDetailScreen
import com.example.detectiveapp.ui.screens.home.HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.detectiveapp.ui.screens.caselist.CaseFilterType


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cases : Screen("cases")
    object CaseForm : Screen("case_form?caseId={caseId}") {
        fun createRoute(caseId: Int? = null): String {
            return if (caseId != null) "case_form?caseId=$caseId" else "case_form"
        }
    }
    object CaseDetail : Screen("case_detail/{caseId}") {
        fun createRoute(caseId: Int): String = "case_detail/$caseId"
    }
}

@Composable
fun DetectiveNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val caseViewModel: CaseViewModel = viewModel(
        factory = CaseViewModelFactory(context)
    )

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // 1. Pantalla de Inicio
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCases = {
                    navController.navigate(Screen.Cases.route)
                },
                onNavigateToNewCase = {
                    navController.navigate(Screen.CaseForm.createRoute())
                },
                onNavigateToClosedCases = {
                    navController.navigate(Screen.Cases.route)
                }
            )
        }

        // 2. Pantalla de Lista de Casos
        composable(route = Screen.Cases.route) {
            CaseListScreen(
                viewModel = caseViewModel,
                initialFilter = CaseFilterType.TODOS,
                onNavigateToDetail = { caseId ->
                    navController.navigate(Screen.CaseDetail.createRoute(caseId))
                },
                onNavigateToCreateCase = {
                    navController.navigate(Screen.CaseForm.createRoute())
                }
            )
        }

        // 3. Pantalla de Formulario (Creación o Edición)
        composable(
            route = Screen.CaseForm.route,
            arguments = listOf(
                navArgument("caseId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val caseIdArg = backStackEntry.arguments?.getInt("caseId")
            val caseId = if (caseIdArg != null && caseIdArg != -1) caseIdArg else null

            CaseFormScreen(
                viewModel = caseViewModel,
                caseId = caseId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 4. Pantalla de Detalle de Caso
        composable(
            route = Screen.CaseDetail.route,
            arguments = listOf(
                navArgument("caseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getInt("caseId") ?: return@composable

            CaseDetailScreen(
                caseId = caseId,
                caseViewModel = caseViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { editId ->
                    navController.navigate(Screen.CaseForm.createRoute(editId))
                }
            )
        }
    }
}