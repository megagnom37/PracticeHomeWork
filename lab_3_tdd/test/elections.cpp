#include "elections.h"
#include <algorithm>

int ElectionsNumber(int aCount, int* pData) {
  if(aCount <= 0) {
    return -1;
  }
  int result(0);
  int countForResult = (aCount + 1) / 2;
  std::sort(pData, pData + aCount);
  for(int i(0); i < countForResult; i++) {
    result += (pData[i] + 1) / 2;
  }
  return result;
}
