/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { EstatusAcademicoDeleteDialogComponent } from 'app/entities/estatus-academico/estatus-academico-delete-dialog.component';
import { EstatusAcademicoService } from 'app/entities/estatus-academico/estatus-academico.service';

describe('Component Tests', () => {
  describe('EstatusAcademico Management Delete Component', () => {
    let comp: EstatusAcademicoDeleteDialogComponent;
    let fixture: ComponentFixture<EstatusAcademicoDeleteDialogComponent>;
    let service: EstatusAcademicoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusAcademicoDeleteDialogComponent]
      })
        .overrideTemplate(EstatusAcademicoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusAcademicoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstatusAcademicoService);
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
