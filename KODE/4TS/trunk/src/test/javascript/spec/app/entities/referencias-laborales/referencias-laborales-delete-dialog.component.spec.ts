/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { ReferenciasLaboralesDeleteDialogComponent } from 'app/entities/referencias-laborales/referencias-laborales-delete-dialog.component';
import { ReferenciasLaboralesService } from 'app/entities/referencias-laborales/referencias-laborales.service';

describe('Component Tests', () => {
  describe('ReferenciasLaborales Management Delete Component', () => {
    let comp: ReferenciasLaboralesDeleteDialogComponent;
    let fixture: ComponentFixture<ReferenciasLaboralesDeleteDialogComponent>;
    let service: ReferenciasLaboralesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ReferenciasLaboralesDeleteDialogComponent]
      })
        .overrideTemplate(ReferenciasLaboralesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReferenciasLaboralesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReferenciasLaboralesService);
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
