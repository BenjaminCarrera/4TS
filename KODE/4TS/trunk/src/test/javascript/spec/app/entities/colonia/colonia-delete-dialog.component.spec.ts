/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { ColoniaDeleteDialogComponent } from 'app/entities/colonia/colonia-delete-dialog.component';
import { ColoniaService } from 'app/entities/colonia/colonia.service';

describe('Component Tests', () => {
  describe('Colonia Management Delete Component', () => {
    let comp: ColoniaDeleteDialogComponent;
    let fixture: ComponentFixture<ColoniaDeleteDialogComponent>;
    let service: ColoniaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ColoniaDeleteDialogComponent]
      })
        .overrideTemplate(ColoniaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColoniaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColoniaService);
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
