/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { TipoIngresoDeleteDialogComponent } from 'app/entities/tipo-ingreso/tipo-ingreso-delete-dialog.component';
import { TipoIngresoService } from 'app/entities/tipo-ingreso/tipo-ingreso.service';

describe('Component Tests', () => {
  describe('TipoIngreso Management Delete Component', () => {
    let comp: TipoIngresoDeleteDialogComponent;
    let fixture: ComponentFixture<TipoIngresoDeleteDialogComponent>;
    let service: TipoIngresoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoIngresoDeleteDialogComponent]
      })
        .overrideTemplate(TipoIngresoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoIngresoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoIngresoService);
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
