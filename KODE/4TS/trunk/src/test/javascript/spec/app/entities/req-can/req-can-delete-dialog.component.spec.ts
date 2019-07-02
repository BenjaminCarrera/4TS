/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { ReqCanDeleteDialogComponent } from 'app/entities/req-can/req-can-delete-dialog.component';
import { ReqCanService } from 'app/entities/req-can/req-can.service';

describe('Component Tests', () => {
  describe('ReqCan Management Delete Component', () => {
    let comp: ReqCanDeleteDialogComponent;
    let fixture: ComponentFixture<ReqCanDeleteDialogComponent>;
    let service: ReqCanService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ReqCanDeleteDialogComponent]
      })
        .overrideTemplate(ReqCanDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReqCanDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReqCanService);
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
