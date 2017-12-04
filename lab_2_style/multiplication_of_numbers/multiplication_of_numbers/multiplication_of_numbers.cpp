#include <iostream>

long long MulOfNumbers(long long aNumber) {
  if(aNumber == 0) {
    return 10;
  } else if(aNumber == 1) {
    return 1;
  }
  //
  long long q(0);
  long long multiplier(1);
  //
  for(long long i(9); i > 1; --i) {
    while((aNumber % i) == 0) {
      q = q + (multiplier * i);
      multiplier = multiplier * 10;
      aNumber = aNumber / i;
    }
  }
  //
  return (aNumber == 1) ? q : -1;
}

int main() {
  long long n;
  std::cin >> n;
  std::cout << MulOfNumbers(n) << std::endl;
  return 0;
}