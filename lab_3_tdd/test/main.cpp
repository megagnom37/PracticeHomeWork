#include "elections.h"

#define MY_DEF_USE_LIBTAP
#ifdef MY_DEF_USE_LIBTAP

#define TAP_COMPILE
#include "libtap\cpp_tap.h"

using namespace std;

int main(int, char *[]) {
  //
  plan_tests(5);
  int* arr = new int[1000];
  //
  arr[0] = 5;
  arr[1] = 7;
  arr[2] = 5;
  ok(ElectionsNumber(3, arr) == 6, "count of 3 groups with data: [5,7,5] is equal 6");
  //
  arr[0] = 3;
  arr[1] = 5;
  arr[2] = 8;
  arr[3] = 9;
  ok(ElectionsNumber(4, arr) == 5, "count of 4 groups with data: [3,5,8,9] is equal 5");
  //
  ok(ElectionsNumber(0, arr) == -1, "invalid count of groups");
  //
  arr[0] = 3;
  ok(ElectionsNumber(1, arr) == 2, "count of 1 groups with data: [3] is equal 2");
  //
  for(int i(0); i < 101; i++) {
    arr[i] = 99;
  }
  ok(ElectionsNumber(101, arr) == 2550, "count of 101 groups with data: [99, ..., 99] is equal 2550");

  delete[] arr;
  return exit_status(); // вывод отчета по тестам

  return 0;
}

#endif
