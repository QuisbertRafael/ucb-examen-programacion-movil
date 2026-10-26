package edu.ucb.pablostify.di

import org.koin.core.module.Module

/** Dependencias que cambian por plataforma (Firebase en Android, version simple en iOS). */
expect fun platformModule(): Module
