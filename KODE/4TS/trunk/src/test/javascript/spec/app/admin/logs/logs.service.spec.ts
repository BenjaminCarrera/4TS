import { TestBed } from '@angular/core/testing';

import { LogsService } from 'app/admin/logs/logs.service';
import { Log } from 'app/admin/logs/log.model';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('Service Tests', () => {
  describe('Logs Service', () => {
    let service: LogsService;
    let httpMock;
    let expectedResult;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });

      expectedResult = {};
      service = TestBed.get(LogsService);
      httpMock = TestBed.get(HttpTestingController);
    });

    afterEach(() => {
      httpMock.verify();
    });

    describe('Service methods', () => {
      it('should call correct URL', () => {
        service.findAll().subscribe();

        const req = httpMock.expectOne({ method: 'GET' });
        const resourceUrl = SERVER_API_URL + 'management/loggers';
        expect(req.request.url).toEqual(resourceUrl);
      });

      it('should change log level', () => {
        service.changeLevel('main', 'ERROR').subscribe();

        const req = httpMock.expectOne({ method: 'POST' });
        const resourceUrl = SERVER_API_URL + 'management/loggers/main';
        expect(req.request.url).toEqual(resourceUrl);
        expect(req.request.body).toEqual({ configuredLevel: 'ERROR' });
      });
    });
  });
});
