/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EstCanInactivoDeleteDialogComponent } from 'app/entities/est-can-inactivo/est-can-inactivo-delete-dialog.component';
import { EstCanInactivoService } from 'app/entities/est-can-inactivo/est-can-inactivo.service';

describe('Component Tests', () => {
  describe('EstCanInactivo Management Delete Component', () => {
    let comp: EstCanInactivoDeleteDialogComponent;
    let fixture: ComponentFixture<EstCanInactivoDeleteDialogComponent>;
    let service: EstCanInactivoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstCanInactivoDeleteDialogComponent]
      })
        .overrideTemplate(EstCanInactivoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstCanInactivoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstCanInactivoService);
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
