package com.example.code.archicomponentssmaples.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by tuozhaobing on 17/12/6.
 */
@Entity
public class User {

  @PrimaryKey
  @NonNull
  public int id;

  @ColumnInfo(name = "name")
  public String name;
}
