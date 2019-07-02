/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EstatusRequerimientoDeleteDialogComponent } from 'app/entities/estatus-requerimiento/estatus-requerimiento-delete-dialog.component';
import { EstatusRequerimientoService } from 'app/entities/estatus-requerimiento/estatus-requerimiento.service';

describe('Component Tests', () => {
  describe('EstatusRequerimiento Management Delete Component', () => {
    let comp: EstatusRequerimientoDeleteDialogComponent;
    let fixture: ComponentFixture<EstatusRequerimientoDeleteDialogComponent>;
    let service: EstatusRequerimientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusRequerimientoDeleteDialogComponent]
      })
        .overrideTemplate(EstatusRequerimientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusRequerimientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusRequerimientoService);
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
