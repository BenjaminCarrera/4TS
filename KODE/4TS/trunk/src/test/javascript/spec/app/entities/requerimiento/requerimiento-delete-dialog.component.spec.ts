/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { RequerimientoDeleteDialogComponent } from 'app/entities/requerimiento/requerimiento-delete-dialog.component';
import { RequerimientoService } from 'app/entities/requerimiento/requerimiento.service';

describe('Component Tests', () => {
  describe('Requerimiento Management Delete Component', () => {
    let comp: RequerimientoDeleteDialogComponent;
    let fixture: ComponentFixture<RequerimientoDeleteDialogComponent>;
    let service: RequerimientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [RequerimientoDeleteDialogComponent]
      })
        .overrideTemplate(RequerimientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RequerimientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RequerimientoService);
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
