cmake_minimum_required(VERSION 3.18)
project(linpack_native)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# --- Пытаемся найти OpenMP ---
find_package(OpenMP)

if(OpenMP_CXX_FOUND)
    message(STATUS "OpenMP found, enabling static linkage")
    add_definitions(-D_OPENMP)
else()
    message(WARNING "OpenMP not found, fallback to single-threaded")
endif()

# --- Настройки архитектур ---
if(ANDROID_ABI STREQUAL "armeabi-v7a")
    add_compile_options(-mfpu=neon -D__ARM_NEON)
elseif(ANDROID_ABI STREQUAL "arm64-v8a")
    message(STATUS "arm64-v8a: NEON включён по умолчанию, без -mfpu")
endif()

# --- Основная библиотека ---
add_library(linpack_native SHARED linpack.cpp)

# --- Статическая линковка OpenMP ---
if(OpenMP_CXX_FOUND)
    target_link_libraries(linpack_native PRIVATE ${OpenMP_CXX_LIB_NAMES})
    # Принудительно добавляем флаги компиляции и линковки
    target_compile_options(linpack_native PRIVATE -fopenmp -Ofast -funroll-loops)
    target_link_options(linpack_native PRIVATE -fopenmp=libomp -static-openmp)
endif()

# --- Линки с Android log для отладки ---
find_library(log-lib log)
target_link_libraries(linpack_native PRIVATE ${log-lib})
