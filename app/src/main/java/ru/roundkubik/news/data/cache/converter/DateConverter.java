package ru.roundkubik.news.data.cache.converter;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @see [Solution from stackoverflow](https://stackoverflow.com/questions/50313525/room-using-date-field)
 */
@ProvidedTypeConverter
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}