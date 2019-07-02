/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EstatusLaboralDeleteDialogComponent } from 'app/entities/estatus-laboral/estatus-laboral-delete-dialog.component';
import { EstatusLaboralService } from 'app/entities/estatus-laboral/estatus-laboral.service';

describe('Component Tests', () => {
  describe('EstatusLaboral Management Delete Component', () => {
    let comp: EstatusLaboralDeleteDialogComponent;
    let fixture: ComponentFixture<EstatusLaboralDeleteDialogComponent>;
    let service: EstatusLaboralService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusLaboralDeleteDialogComponent]
      })
        .overrideTemplate(EstatusLaboralDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusLaboralDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusLaboralService);
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
