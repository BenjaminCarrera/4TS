/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EstatusReqCanRecDeleteDialogComponent } from 'app/entities/estatus-req-can-rec/estatus-req-can-rec-delete-dialog.component';
import { EstatusReqCanRecService } from 'app/entities/estatus-req-can-rec/estatus-req-can-rec.service';

describe('Component Tests', () => {
  describe('EstatusReqCanRec Management Delete Component', () => {
    let comp: EstatusReqCanRecDeleteDialogComponent;
    let fixture: ComponentFixture<EstatusReqCanRecDeleteDialogComponent>;
    let service: EstatusReqCanRecService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusReqCanRecDeleteDialogComponent]
      })
        .overrideTemplate(EstatusReqCanRecDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusReqCanRecDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusReqCanRecService);
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
