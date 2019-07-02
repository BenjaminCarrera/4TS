/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { PrioridadReqDeleteDialogComponent } from 'app/entities/prioridad-req/prioridad-req-delete-dialog.component';
import { PrioridadReqService } from 'app/entities/prioridad-req/prioridad-req.service';

describe('Component Tests', () => {
  describe('PrioridadReq Management Delete Component', () => {
    let comp: PrioridadReqDeleteDialogComponent;
    let fixture: ComponentFixture<PrioridadReqDeleteDialogComponent>;
    let service: PrioridadReqService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [PrioridadReqDeleteDialogComponent]
      })
        .overrideTemplate(PrioridadReqDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrioridadReqDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrioridadReqService);
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
