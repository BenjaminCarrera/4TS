/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { TipoPeriodoDeleteDialogComponent } from 'app/entities/tipo-periodo/tipo-periodo-delete-dialog.component';
import { TipoPeriodoService } from 'app/entities/tipo-periodo/tipo-periodo.service';

describe('Component Tests', () => {
  describe('TipoPeriodo Management Delete Component', () => {
    let comp: TipoPeriodoDeleteDialogComponent;
    let fixture: ComponentFixture<TipoPeriodoDeleteDialogComponent>;
    let service: TipoPeriodoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoPeriodoDeleteDialogComponent]
      })
        .overrideTemplate(TipoPeriodoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoPeriodoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPeriodoService);
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
