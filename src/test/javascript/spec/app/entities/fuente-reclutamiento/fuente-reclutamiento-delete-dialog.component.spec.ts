/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { FuenteReclutamientoDeleteDialogComponent } from 'app/entities/fuente-reclutamiento/fuente-reclutamiento-delete-dialog.component';
import { FuenteReclutamientoService } from 'app/entities/fuente-reclutamiento/fuente-reclutamiento.service';

describe('Component Tests', () => {
  describe('FuenteReclutamiento Management Delete Component', () => {
    let comp: FuenteReclutamientoDeleteDialogComponent;
    let fixture: ComponentFixture<FuenteReclutamientoDeleteDialogComponent>;
    let service: FuenteReclutamientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [FuenteReclutamientoDeleteDialogComponent]
      })
        .overrideTemplate(FuenteReclutamientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuenteReclutamientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuenteReclutamientoService);
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
