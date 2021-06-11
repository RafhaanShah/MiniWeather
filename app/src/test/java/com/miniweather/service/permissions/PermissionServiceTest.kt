package com.miniweather.service.permissions

import android.Manifest
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.miniweather.testutil.BaseInstrumentedTest
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PermissionServiceTest : BaseInstrumentedTest() {

    @MockK
    private lateinit var mockFragment: Fragment

    @MockK
    private lateinit var mockActivityResultLauncher: ActivityResultLauncher<String>

    private lateinit var activityResultSlot: CapturingSlot<ActivityResultCallback<Boolean>>

    private lateinit var permissionService: PermissionService

    @Before
    fun setUp() {
        activityResultSlot = slot()
        every {
            mockFragment.registerForActivityResult<String, Boolean>(
                any(),
                capture(activityResultSlot)
            )
        } returns mockActivityResultLauncher
        permissionService = PermissionService()
        permissionService.registerForPermissions(mockFragment)
    }

    @Test
    fun whenRequestPermission_andPermissionHasNotBeenGranted_requestsPermission() =
        runBlockingTest {
            val expected = true
            every { mockActivityResultLauncher.launch(any()) } answers {
                activityResultSlot.captured.onActivityResult(expected)
            }

            val actual = permissionService.requestPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            assertEquals(expected, actual)
        }

    @Test
    fun whenRequestPermission_andPermissionAlreadyGranted_returnsTrue() = runBlockingTest {
        shadowApplication.grantPermissions(Manifest.permission.ACCESS_FINE_LOCATION)

        val actual = permissionService.requestPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        assertTrue(actual)
    }

    @Test(expected = IllegalStateException::class)
    fun whenRequestPermission_withoutRegistering_throwsError() = runBlockingTest {
        val unRegisteredPermissionsService = PermissionService()
        unRegisteredPermissionsService.requestPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

}
