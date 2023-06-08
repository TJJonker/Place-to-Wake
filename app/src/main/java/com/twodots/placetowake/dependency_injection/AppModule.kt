package com.twodots.placetowake.dependency_injection

import android.app.Application
import androidx.room.Room
import com.twodots.placetowake.feature_geo_alarm.data.data_source.AlarmDatabase
import com.twodots.placetowake.feature_geo_alarm.data.repository.AlarmRepositoryImplementation
import com.twodots.placetowake.feature_geo_alarm.domain.repository.AlarmRepository
import com.twodots.placetowake.feature_geo_alarm.domain.use_case.AddAlarmUseCase
import com.twodots.placetowake.feature_geo_alarm.domain.use_case.AlarmUseCases
import com.twodots.placetowake.feature_geo_alarm.domain.use_case.DeleteAlarmUseCase
import com.twodots.placetowake.feature_geo_alarm.domain.use_case.GetAlarmsUseCase
import com.twodots.placetowake.feature_geo_alarm.presentation.util.RingtonePicker
import com.twodots.placetowake.feature_geo_alarm.presentation.util.permission.PermissionDialogManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideAlarmDatabase(app: Application): AlarmDatabase {
        return Room.databaseBuilder(
            app,
            AlarmDatabase::class.java,
            AlarmDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(db: AlarmDatabase): AlarmRepository {
        return AlarmRepositoryImplementation(db.alarmDao)
    }

    @Provides
    @Singleton
    fun provideAlarmUseCases(repository: AlarmRepository): AlarmUseCases {
        return AlarmUseCases(
            getAlarms = GetAlarmsUseCase(repository = repository),
            deleteAlarm = DeleteAlarmUseCase(repository = repository),
            addAlarm = AddAlarmUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun providePermissionManager(): PermissionDialogManager {
        return PermissionDialogManager()
    }

    @Provides
    @Singleton
    fun provideRingtonePicker(application: Application): RingtonePicker{
        return RingtonePicker(application)
    }
}