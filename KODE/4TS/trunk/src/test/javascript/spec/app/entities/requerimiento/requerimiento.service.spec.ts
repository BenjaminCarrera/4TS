/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RequerimientoService } from 'app/entities/requerimiento/requerimiento.service';
import { IRequerimiento, Requerimiento } from 'app/shared/model/requerimiento.model';

describe('Service Tests', () => {
  describe('Requerimiento Service', () => {
    let injector: TestBed;
    let service: RequerimientoService;
    let httpMock: HttpTestingController;
    let elemDefault: IRequerimiento;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(RequerimientoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Requerimiento(
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaAlda: currentDate.format(DATE_TIME_FORMAT),
            fechaResolucion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Requerimiento', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaAlda: currentDate.format(DATE_TIME_FORMAT),
            fechaResolucion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaAlda: currentDate,
            fechaResolucion: currentDate
          },
          returnedFromService
        );
        service
          .create(new Requerimiento(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Requerimiento', async () => {
        const returnedFromService = Object.assign(
          {
            fechaAlda: currentDate.format(DATE_TIME_FORMAT),
            fechaResolucion: currentDate.format(DATE_TIME_FORMAT),
            remplazoDe: 'BBBBBB',
            vacantesSolicitadas: 1,
            proyecto: 'BBBBBB',
            nombreContacto: 'BBBBBB',
            tarifaSueldoNet: 1,
            prestaciones: 'BBBBBB',
            duracionAsignacion: 1,
            lugarTrabajo: 'BBBBBB',
            coorLat: 1,
            coorLong: 1,
            horario: 'BBBBBB',
            informacionExamen: 'BBBBBB',
            informacionAdicional: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlda: currentDate,
            fechaResolucion: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Requerimiento', async () => {
        const returnedFromService = Object.assign(
          {
            fechaAlda: currentDate.format(DATE_TIME_FORMAT),
            fechaResolucion: currentDate.format(DATE_TIME_FORMAT),
            remplazoDe: 'BBBBBB',
            vacantesSolicitadas: 1,
            proyecto: 'BBBBBB',
            nombreContacto: 'BBBBBB',
            tarifaSueldoNet: 1,
            prestaciones: 'BBBBBB',
            duracionAsignacion: 1,
            lugarTrabajo: 'BBBBBB',
            coorLat: 1,
            coorLong: 1,
            horario: 'BBBBBB',
            informacionExamen: 'BBBBBB',
            informacionAdicional: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaAlda: currentDate,
            fechaResolucion: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Requerimiento', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
