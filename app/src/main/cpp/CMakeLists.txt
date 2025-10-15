cmake_minimum_required(VERSION 3.18)
project(linpack_native)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(OpenMP)

if(OpenMP_CXX_FOUND)
    message(STATUS "OpenMP found, enabling static linkage")
    add_definitions(-D_OPENMP)
else()
    message(WARNING "OpenMP not found, fallback to single-threaded")
endif()

if(ANDROID_ABI STREQUAL "armeabi-v7a")
    add_compile_options(-mfpu=neon -D__ARM_NEON)
elseif(ANDROID_ABI STREQUAL "arm64-v8a")
    message(STATUS "arm64-v8a: NEON включён по умолчанию, без -mfpu")
endif()

add_library(linpack_native SHARED linpack.cpp)

if(OpenMP_CXX_FOUND)
    target_link_libraries(linpack_native PRIVATE ${OpenMP_CXX_LIB_NAMES})
    target_compile_options(linpack_native PRIVATE -fopenmp -Ofast -funroll-loops)
    target_link_options(linpack_native PRIVATE -fopenmp=libomp -static-openmp)
endif()

find_library(log-lib log)
target_link_libraries(linpack_native PRIVATE ${log-lib})
