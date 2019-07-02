/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { FormacionAcademicaDeleteDialogComponent } from 'app/entities/formacion-academica/formacion-academica-delete-dialog.component';
import { FormacionAcademicaService } from 'app/entities/formacion-academica/formacion-academica.service';

describe('Component Tests', () => {
  describe('FormacionAcademica Management Delete Component', () => {
    let comp: FormacionAcademicaDeleteDialogComponent;
    let fixture: ComponentFixture<FormacionAcademicaDeleteDialogComponent>;
    let service: FormacionAcademicaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [FormacionAcademicaDeleteDialogComponent]
      })
        .overrideTemplate(FormacionAcademicaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormacionAcademicaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FormacionAcademicaService);
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
