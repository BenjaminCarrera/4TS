/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { TipoSolicitudDeleteDialogComponent } from 'app/entities/tipo-solicitud/tipo-solicitud-delete-dialog.component';
import { TipoSolicitudService } from 'app/entities/tipo-solicitud/tipo-solicitud.service';

describe('Component Tests', () => {
  describe('TipoSolicitud Management Delete Component', () => {
    let comp: TipoSolicitudDeleteDialogComponent;
    let fixture: ComponentFixture<TipoSolicitudDeleteDialogComponent>;
    let service: TipoSolicitudService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoSolicitudDeleteDialogComponent]
      })
        .overrideTemplate(TipoSolicitudDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoSolicitudDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoSolicitudService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
