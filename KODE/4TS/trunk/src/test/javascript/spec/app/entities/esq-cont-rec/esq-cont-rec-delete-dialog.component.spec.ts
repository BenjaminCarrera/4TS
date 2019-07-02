/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EsqContRecDeleteDialogComponent } from 'app/entities/esq-cont-rec/esq-cont-rec-delete-dialog.component';
import { EsqContRecService } from 'app/entities/esq-cont-rec/esq-cont-rec.service';

describe('Component Tests', () => {
  describe('EsqContRec Management Delete Component', () => {
    let comp: EsqContRecDeleteDialogComponent;
    let fixture: ComponentFixture<EsqContRecDeleteDialogComponent>;
    let service: EsqContRecService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EsqContRecDeleteDialogComponent]
      })
        .overrideTemplate(EsqContRecDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EsqContRecDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EsqContRecService);
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
