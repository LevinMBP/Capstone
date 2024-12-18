import com.android.build.gradle.internal.dsl.decorator.SupportedPropertyType.Collection.List.type
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}


tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}

