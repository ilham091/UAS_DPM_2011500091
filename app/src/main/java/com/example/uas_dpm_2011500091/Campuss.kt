package com.example.uas_dpm_2011500091

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Campuss(context: Context): SQLiteOpenHelper(context, "kampus", null, 1){
    var nidn = ""
    var namaDosen = ""
    var jabatan = ""
    var glgpngkt =""
    var pendidikan =""
    var keahlian = ""
    var prgstdi = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            nidn char(10) primary key,
            nm_dosen varchar(50) not null,
            jabatan varchar (15) not null,
            golonganpangkat varchar (30) not null,
            pendidikan char (2) not null,
            keahlian varchar (30) not null,
            programstudi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nidn", nidn)
            put("nm_dosen", namaDosen)
            put("jabatan", jabatan)
            put("golonganpangkat", glgpngkt)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("programstudi", prgstdi)
        }
        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }
    fun ubah(kode:String): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nm_dosen", namaDosen)
            put("jabatan", jabatan)
            put("golonganpangkat", glgpngkt)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("programstudi", prgstdi)
        }
        val cmd = db.update(tabel, cv, "nidn = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }
    fun hapus(kode:String): Boolean {
        val db = writableDatabase
        val cmd = db.delete(tabel, "nidn = ?", arrayOf(kode))
        return cmd != -1
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }
}