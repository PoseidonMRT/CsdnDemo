package com.example.code.archicomponentssmaples.room;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

/**
 * Created by tuozhaobing on 17/12/6.
 */
@Dao
public interface UserDao {
  @Query("select * from user")
  List<User> loadAllUsers();

  @Query("select * from user where id = :id")
  User loadUserById(int id);

  @Query("select * from user where name = :name ")
  List<User> findUserByName(String name);

  @Insert(onConflict = IGNORE)
  void insertUser(User user);

  @Delete
  void deleteUser(User user);

  @Query("delete from user where name like :badName")
  int deleteUsersByName(String badName);

  @Insert(onConflict = IGNORE)
  void insertOrReplaceUsers(User... users);

  @Delete
  void deleteUsers(User user1, User user2);

  @Query("DELETE FROM User")
  void deleteAll();
}
