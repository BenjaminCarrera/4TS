import { HttpParams } from '@angular/common/http';

export const createRequestOption = (req?: any): HttpParams => {
  let options: HttpParams = new HttpParams();
  if (req) {
    Object.keys(req).forEach(key => {
      if (key.endsWith('.in')) {
        req[key].forEach(valin => {
          options = options.set(key, valin);
        });
      }
      if (key !== 'sort' && key !== 'criteria') {
        options = options.set(key, req[key]);
      }
    });
    if (req.criteria) {
      req.criteria.forEach(criterion => {
        options = options.set(criterion.key, criterion.value);
      });
    }
    if (req.sort) {
      req.sort.forEach(val => {
        options = options.append('sort', val);
      });
    }
  }
  return options;
};
