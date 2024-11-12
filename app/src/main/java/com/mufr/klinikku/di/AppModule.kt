package com.mufr.klinikku.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.mufr.klinikku.BuildConfig
import com.mufr.klinikku.data.local.AuthService
import com.mufr.klinikku.data.remote.api_service.AppointmentApiService
import com.mufr.klinikku.data.remote.api_service.AuthApiService
import com.mufr.klinikku.data.remote.api_service.HospitalApiService
import com.mufr.klinikku.data.remote.api_service.PatientApiService
import com.mufr.klinikku.data.remote.api_service.QueueApiService
import com.mufr.klinikku.data.remote.repository.AppointmentRepositoryImpl
import com.mufr.klinikku.data.remote.repository.AuthRepositoryImpl
import com.mufr.klinikku.data.remote.repository.HospitalRepositoryImpl
import com.mufr.klinikku.data.remote.repository.PatientRepositoryImpl
import com.mufr.klinikku.data.remote.repository.QueueRepositoryImpl
import com.mufr.klinikku.domain.repository.AppointmentRepository
import com.mufr.klinikku.domain.repository.AuthRepository
import com.mufr.klinikku.domain.repository.HospitalRepository
import com.mufr.klinikku.domain.repository.PatientRepository
import com.mufr.klinikku.domain.repository.QueueRepository
import com.mufr.klinikku.domain.use_cases.appoinment.ChangeAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.appoinment.CreateAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.appoinment.GetAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.auth.LoginUseCase
import com.mufr.klinikku.domain.use_cases.auth.RegisterUseCase
import com.mufr.klinikku.domain.use_cases.hospital.GetDoctorAppointmentScheduleUseCase
import com.mufr.klinikku.domain.use_cases.hospital.GetHospitalDoctorAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.hospital.GetHospitalDoctorOnDutyUseCase
import com.mufr.klinikku.domain.use_cases.hospital.GetHospitalUseCase
import com.mufr.klinikku.domain.use_cases.patient.AddPatientUseCase
import com.mufr.klinikku.domain.use_cases.patient.EditPatientUseCase
import com.mufr.klinikku.domain.use_cases.patient.GetAllPatientUseCase
import com.mufr.klinikku.domain.use_cases.queue.AddPatientToQueueUseCase
import com.mufr.klinikku.domain.use_cases.queue.GetQueueUseCase
import com.mufr.klinikku.shared.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(Constant.AUTH_PREFERENCES)
            }
        )

    @Provides
    @Singleton
    fun provideAuthPreferenceDataStore(dataStore: DataStore<Preferences>) =
        AuthService(dataStore)

    val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(loggingInterceptor)
        }
        val client = clientBuilder
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService,
        authService: AuthService
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApiService,
            authService
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        authRepository: AuthRepository
    ): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideHospitalApiService(): HospitalApiService {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(loggingInterceptor)
        }
        val client = clientBuilder
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(HospitalApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHospitalRepository(
        authService: AuthService,
        hospitalApiService: HospitalApiService
    ): HospitalRepository {
        return HospitalRepositoryImpl(
            userPreference = authService,
            hospitalApiService = hospitalApiService
        )
    }

    @Provides
    @Singleton
    fun provideGetHospitalUseCase(
        hospitalRepository: HospitalRepository
    ): GetHospitalUseCase {
        return GetHospitalUseCase(
            hospitalRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetHospitalDoctorAppointmentUseCase(
        hospitalRepository: HospitalRepository
    ): GetHospitalDoctorAppointmentUseCase {
        return GetHospitalDoctorAppointmentUseCase(
            hospitalRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetHospitalDoctorOnDutyUseCase(
        hospitalRepository: HospitalRepository
    ): GetHospitalDoctorOnDutyUseCase {
        return GetHospitalDoctorOnDutyUseCase(
            hospitalRepository
        )
    }

    @Provides
    @Singleton
    fun providePatientApiService(): PatientApiService {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(loggingInterceptor)
        }
        val client = clientBuilder
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PatientApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePatientRepository(
        authService: AuthService,
        patientApiService: PatientApiService
    ): PatientRepository {
        return PatientRepositoryImpl(
            authService,
            patientApiService
        )
    }

    @Provides
    @Singleton
    fun provideGetAllPatientUseCase(
        patientRepository: PatientRepository
    ): GetAllPatientUseCase {
        return GetAllPatientUseCase(
            patientRepository
        )
    }

    @Provides
    @Singleton
    fun provideAddPatientUseCase(
        patientRepository: PatientRepository
    ): AddPatientUseCase {
        return AddPatientUseCase(
            patientRepository
        )
    }

    @Provides
    @Singleton
    fun provideEditPatientUseCase(
        patientRepository: PatientRepository
    ): EditPatientUseCase {
        return EditPatientUseCase(
            patientRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetDoctorAppointmentAvailableScheduleUseCase(
        hospitalRepository: HospitalRepository
    ): GetDoctorAppointmentScheduleUseCase {
        return GetDoctorAppointmentScheduleUseCase(
            hospitalRepository
        )
    }

    @Provides
    @Singleton
    fun provideAppointmentApiService(): AppointmentApiService {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(loggingInterceptor)
        }
        val client = clientBuilder
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AppointmentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppointmentRepository(
        authService: AuthService,
        appointmentApiService: AppointmentApiService
    ): AppointmentRepository {
        return AppointmentRepositoryImpl(
            authService,
            appointmentApiService
        )
    }

    @Provides
    @Singleton
    fun provideCreateAppointmentUseCase(
        appointmentRepository: AppointmentRepository
    ): CreateAppointmentUseCase {
        return CreateAppointmentUseCase(
            appointmentRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetAppointmentUseCase(
        appointmentRepository: AppointmentRepository
    ): GetAppointmentUseCase {
        return GetAppointmentUseCase(
            appointmentRepository
        )
    }

    @Provides
    @Singleton
    fun provideChangeAppointmentUseCase(
        appointmentRepository: AppointmentRepository
    ): ChangeAppointmentUseCase {
        return ChangeAppointmentUseCase(
            appointmentRepository
        )
    }

    @Provides
    @Singleton
    fun provideQueueApiService(): QueueApiService {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(loggingInterceptor)
        }
        val client = clientBuilder
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(QueueApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideQueueRepository(
        authService: AuthService,
        queueApiService: QueueApiService
    ): QueueRepository {
        return QueueRepositoryImpl(
            authService,
            queueApiService
        )
    }

    @Provides
    @Singleton
    fun provideAddPatientToQueue(
        queueRepository: QueueRepository
    ): AddPatientToQueueUseCase {
        return AddPatientToQueueUseCase(
            queueRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetQueueUseCase(
        queueRepository: QueueRepository
    ): GetQueueUseCase {
        return GetQueueUseCase(
            queueRepository
        )
    }
}