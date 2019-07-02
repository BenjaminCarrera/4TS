/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EstatusTareaDeleteDialogComponent } from 'app/entities/estatus-tarea/estatus-tarea-delete-dialog.component';
import { EstatusTareaService } from 'app/entities/estatus-tarea/estatus-tarea.service';

describe('Component Tests', () => {
  describe('EstatusTarea Management Delete Component', () => {
    let comp: EstatusTareaDeleteDialogComponent;
    let fixture: ComponentFixture<EstatusTareaDeleteDialogComponent>;
    let service: EstatusTareaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusTareaDeleteDialogComponent]
      })
        .overrideTemplate(EstatusTareaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusTareaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusTareaService);
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
