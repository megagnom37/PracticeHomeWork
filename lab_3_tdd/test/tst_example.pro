CONFIG += c++11 console

SOURCES += main.cpp \
    elections.cpp

HEADERS += \
    libtap/tap.c \
    libtap/tap.h \
    libtap/cpp_patch_tap.h \
    libtap/cpp_tap.h \
    libtap/tap.h \
    elections.h

QMAKE_CXXFLAGS += -fprofile-arcs -ftest-coverage -O0

QMAKE_LFLAGS += -fprofile-arcs -ftest-coverage -O0

LIBS += -lgcov
