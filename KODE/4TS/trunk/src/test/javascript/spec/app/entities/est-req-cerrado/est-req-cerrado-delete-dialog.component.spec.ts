/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EstReqCerradoDeleteDialogComponent } from 'app/entities/est-req-cerrado/est-req-cerrado-delete-dialog.component';
import { EstReqCerradoService } from 'app/entities/est-req-cerrado/est-req-cerrado.service';

describe('Component Tests', () => {
  describe('EstReqCerrado Management Delete Component', () => {
    let comp: EstReqCerradoDeleteDialogComponent;
    let fixture: ComponentFixture<EstReqCerradoDeleteDialogComponent>;
    let service: EstReqCerradoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstReqCerradoDeleteDialogComponent]
      })
        .overrideTemplate(EstReqCerradoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstReqCerradoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstReqCerradoService);
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
