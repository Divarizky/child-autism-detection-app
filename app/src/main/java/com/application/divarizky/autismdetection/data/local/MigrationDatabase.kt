package com.application.divarizky.autismdetection.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Migrasi dari versi 1 ke versi 2: Menambahkan kolom users_profile dan mempertahankan semua kolom yang ada
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Buat tabel baru dengan skema lengkap
        db.execSQL("""
            CREATE TABLE users_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                username TEXT NOT NULL,
                email TEXT NOT NULL,
                password TEXT NOT NULL,
                isLoggedIn INTEGER NOT NULL,
                users_profile TEXT
            )
        """.trimIndent())

        // Salin data dari tabel lama ke tabel baru
        db.execSQL("""
            INSERT INTO users_new (id, username, email, password, isLoggedIn)
            SELECT id, username, email, password, isLoggedIn FROM users
        """.trimIndent())

        // Hapus tabel lama
        db.execSQL("DROP TABLE users")

        // Ganti nama tabel baru menjadi 'users'
        db.execSQL("ALTER TABLE users_new RENAME TO users")
    }
}
