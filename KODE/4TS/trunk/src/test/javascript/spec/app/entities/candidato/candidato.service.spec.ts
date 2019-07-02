/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CandidatoService } from 'app/entities/candidato/candidato.service';
import { ICandidato, Candidato, Sexo, EstadoCivil } from 'app/shared/model/candidato.model';

describe('Service Tests', () => {
  describe('Candidato Service', () => {
    let injector: TestBed;
    let service: CandidatoService;
    let httpMock: HttpTestingController;
    let elemDefault: ICandidato;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CandidatoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Candidato(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        currentDate,
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Sexo.MASCULINO,
        EstadoCivil.SOLTERO,
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            disponibilidadEntrevistaFecha: currentDate.format(DATE_FORMAT),
            disponibilidadAsignacionFecha: currentDate.format(DATE_FORMAT),
            fechaAlta: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimoSeguimiento: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Candidato', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            disponibilidadEntrevistaFecha: currentDate.format(DATE_FORMAT),
            disponibilidadAsignacionFecha: currentDate.format(DATE_FORMAT),
            fechaAlta: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimoSeguimiento: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNacimiento: currentDate,
            disponibilidadEntrevistaFecha: currentDate,
            disponibilidadAsignacionFecha: currentDate,
            fechaAlta: currentDate,
            fechaUltimoSeguimiento: currentDate
          },
          returnedFromService
        );
        service
          .create(new Candidato(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Candidato', async () => {
        const returnedFromService = Object.assign(
          {
            anosExperiencia: 1,
            nombre: 'BBBBBB',
            apellidoPaterno: 'BBBBBB',
            apellidoMaterno: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            edad: 1,
            emailPrincipal: 'BBBBBB',
            emailAdicional: 'BBBBBB',
            emailAsignacion: 'BBBBBB',
            emailKode: 'BBBBBB',
            web: 'BBBBBB',
            telefonoCasa: 'BBBBBB',
            telefonoTrabajo: 'BBBBBB',
            telefonoCelular: 'BBBBBB',
            telefonoAdicional: 'BBBBBB',
            coorLat: 1,
            coorLong: 1,
            dirCodigoPostal: 'BBBBBB',
            dirCalle: 'BBBBBB',
            noExt: 'BBBBBB',
            noInt: 'BBBBBB',
            salarioNeto: 1,
            costoTotal: 1,
            contatoDuracionMinima: 1,
            disponibilidadEntrevistaFecha: currentDate.format(DATE_FORMAT),
            disponibilidadEntrevistaPeriodo: 1,
            disponibilidadAsignacionFecha: currentDate.format(DATE_FORMAT),
            disponibilidadAsignacionPeriodo: 'BBBBBB',
            antecedenteUltimoEmpleador: 'BBBBBB',
            antecedenteSalarioNeto: 1,
            notas: 'BBBBBB',
            porcentajeIngles: 1,
            curp: 'BBBBBB',
            rfc: 'BBBBBB',
            nss: 'BBBBBB',
            sexo: 'BBBBBB',
            estadoCivil: 'BBBBBB',
            fechaAlta: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimoSeguimiento: currentDate.format(DATE_TIME_FORMAT),
            foto: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaNacimiento: currentDate,
            disponibilidadEntrevistaFecha: currentDate,
            disponibilidadAsignacionFecha: currentDate,
            fechaAlta: currentDate,
            fechaUltimoSeguimiento: currentDate
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

      it('should return a list of Candidato', async () => {
        const returnedFromService = Object.assign(
          {
            anosExperiencia: 1,
            nombre: 'BBBBBB',
            apellidoPaterno: 'BBBBBB',
            apellidoMaterno: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            edad: 1,
            emailPrincipal: 'BBBBBB',
            emailAdicional: 'BBBBBB',
            emailAsignacion: 'BBBBBB',
            emailKode: 'BBBBBB',
            web: 'BBBBBB',
            telefonoCasa: 'BBBBBB',
            telefonoTrabajo: 'BBBBBB',
            telefonoCelular: 'BBBBBB',
            telefonoAdicional: 'BBBBBB',
            coorLat: 1,
            coorLong: 1,
            dirCodigoPostal: 'BBBBBB',
            dirCalle: 'BBBBBB',
            noExt: 'BBBBBB',
            noInt: 'BBBBBB',
            salarioNeto: 1,
            costoTotal: 1,
            contatoDuracionMinima: 1,
            disponibilidadEntrevistaFecha: currentDate.format(DATE_FORMAT),
            disponibilidadEntrevistaPeriodo: 1,
            disponibilidadAsignacionFecha: currentDate.format(DATE_FORMAT),
            disponibilidadAsignacionPeriodo: 'BBBBBB',
            antecedenteUltimoEmpleador: 'BBBBBB',
            antecedenteSalarioNeto: 1,
            notas: 'BBBBBB',
            porcentajeIngles: 1,
            curp: 'BBBBBB',
            rfc: 'BBBBBB',
            nss: 'BBBBBB',
            sexo: 'BBBBBB',
            estadoCivil: 'BBBBBB',
            fechaAlta: currentDate.format(DATE_TIME_FORMAT),
            fechaUltimoSeguimiento: currentDate.format(DATE_TIME_FORMAT),
            foto: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaNacimiento: currentDate,
            disponibilidadEntrevistaFecha: currentDate,
            disponibilidadAsignacionFecha: currentDate,
            fechaAlta: currentDate,
            fechaUltimoSeguimiento: currentDate
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

      it('should delete a Candidato', async () => {
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
